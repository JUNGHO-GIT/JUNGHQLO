#!/bin/bash

# OS 확인 ##########################################################################################
if [[ "$OSTYPE" == "msys" ]]; then
  OS="win"
else
  OS="linux"
fi
echo "Activated OS is : $OS"

# 변경 로그 수정 ###################################################################################
modify_changelog() {

  echo "Modifying changelog.md file..."

  local current_date=$(date +"%Y-%m-%d")
  local current_time=$(date +"%H:%M:%S")
  local last_version=$(grep -oP '\d+\.\d+\.\d+' changelog.md | tail -1)
  local new_version=$(echo $last_version | awk -F. -v OFS=. '{$NF += 1 ; print}')
  echo -e "\n### \[ $new_version \]\n\n- $current_date ($current_time)" >> changelog.md

  echo "Changelog updated to version $new_version"
}

# appliction.properties 파일 수정 ##################################################################
modify_application_properties() {

  echo "Modifying application.properties file..."

  local file_path="src/main/resources/application.properties"

  # spring.devtools로 시작하는 모든 줄을 주석 처리
  local devtoolsPattern="^spring.devtools"
  sed -i "s|$devtoolsPattern|# &|" "$file_path"

  # orders-url 설정 변경
  local ordersUrlPattern="^orders-url=http://localhost:8080/JUNGHQLO/orders"
  sed -i "s|$ordersUrlPattern|orders-url=http://104.196.212.101:8080/JUNGHQLO/orders|" "$file_path"

  echo "application.properties file updated."
}

# Git 푸시 (public) ################################################################################
git_push_public() {
  echo "Pushing changes to public repository..."

  local ignoreFile=".gitignore"
  local ignorePublicFile=".gitignore.public"

  # public 파일 내용을 .gitignore로 복사 (기존 내용 삭제)
  if [ -f $ignorePublicFile ]; then
    echo "" > $ignoreFile
    cat $ignorePublicFile > $ignoreFile
  fi

  git rm -r --cached .
  git add .
  git commit -m "$(date +"%Y-%m-%d %H:%M:%S")"
  git push --force origin master

  # .gitignore 파일 복구
  if [ -f $ignorePublicFile ]; then
    echo "" > $ignoreFile
    cat $ignorePublicFile > $ignoreFile
  fi

  echo "Changes pushed to public repository."
}

# Git 푸시 (private) ###############################################################################
git_push_private() {
  echo "Pushing changes to private repository..."

  local ignoreFile=".gitignore"
  local ignorePublicFile=".gitignore.public"
  local ignorePrivateFile=".gitignore.private"

  # private 파일 내용을 .gitignore로 복사 (기존 내용 삭제)
  if [ -f $ignorePrivateFile ]; then
    echo "" > $ignoreFile
    cat $ignorePrivateFile > $ignoreFile
  fi

  git rm -r --cached .
  git add .
  git commit -m "$(date +"%Y-%m-%d %H:%M:%S")"
  git push --force private master

  # .gitignore 파일 복구
  if [ -f $ignorePublicFile ]; then
    echo "" > $ignoreFile
    cat $ignorePublicFile > $ignoreFile
  fi

  echo "Changes pushed to private repository."
}

# 프로젝트 빌드 ####################################################################################
build_project() {
  echo "Project build started..."

  if mvn clean package; then
    echo "Project build completed successfully."
  else
    echo "Project build failed. Aborting upload."
    exit 1
  fi
}

# gcloud에 업로드 ##################################################################################
upload_to_gcloud() {
  echo "Uploading file to gcloud..."

  if gcloud storage cp target/JUNGHQLO.war gs://jungho-bucket/JUNGHQLO/SERVER/JUNGHQLO.war; then
    echo "File successfully uploaded to gcloud."
    run_remote_script
  else
    echo "File upload failed. Aborting remote execution."
    exit 1
  fi
}

# 원격 서버에서 작업 수행 ##########################################################################
run_remote_script() {

  echo "Running remote script..."

  local key_path="C:\\Users\\jungh\\.ssh\\JKEY"
  local ip_addr="104.196.212.101"
  local name="junghomun00"

  echo "Connecting to $name@$ip_addr with key $key_path"

  ssh -t -o StrictHostKeyChecking=no -i "$key_path" "$name@$ip_addr" << EOF
    cd /usr/share/tomcat9/webapps
    sudo rm -rf JUNGHQLO JUNGHQLO.war
    sudo gcloud storage cp gs://jungho-bucket/JUNGHQLO/SERVER/JUNGHQLO.war .
    sudo unzip JUNGHQLO.war -d JUNGHQLO
    sudo systemctl restart tomcat9
    exit
EOF

  echo "Remote server updated and application restarted."
}

# properties 파일 복구 ############################################################################
recover_application_properties() {

  echo "Recovering application.properties file..."

  local file_path="src/main/resources/application.properties"

  # 주석 처리된 spring.devtools 복구
  local devtoolsPattern="^# spring.devtools"
  sed -i "s|$devtoolsPattern|spring.devtools|" "$file_path"

  # orders-url 설정을 localhost로 복구
  local ordersUrlPattern="^orders-url=http://104.196.212.101:8080/JUNGHQLO/orders"
  sed -i "s|$ordersUrlPattern|orders-url=http://localhost:8080/JUNGHQLO/orders|" "$file_path"

  echo "application.properties file recovered."
}

# 실행 #############################################################################################
modify_changelog
modify_application_properties
git_push_public
git_push_private
build_project
upload_to_gcloud
run_remote_script
recover_application_properties