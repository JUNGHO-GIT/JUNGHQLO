// server/gcloud.cjs

const os = require('os');
const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

// 프로젝트 설정 -------------------------------------------------------------------------------
const CONFIG = {
	domain: `junghomun.com`,
	projectName: `JUNGHQLO`,
	serverIp: `104.196.212.101`,
	localPort: 8080,
	paths: {
		root: path.resolve(__dirname, '..'),
		node: __dirname,
		changelog: path.resolve(__dirname, '..', 'changelog.md'),
		properties: path.resolve(__dirname, '..', 'src', 'main', 'resources', 'application.properties'),
		gitignorePublic: path.resolve(__dirname, '..', '.gitignore.public'),
		gitignorePrivate: path.resolve(__dirname, '..', '.gitignore.private'),
		gitignore: path.resolve(__dirname, '..', '.gitignore'),
		target: path.resolve(__dirname, '..', 'target', 'JUNGHQLO.war')
	},
	gcs: {
		bucket: `jungho-bucket`,
		path: `JUNGHQLO/SERVER/JUNGHQLO.war`
	},
	ssh: {
		win: {
			keyPath: `C:\\Users\\jungh\\.ssh\\JKEY`,
			serviceId: `junghomun00`
		},
		linux: {
			keyPath: `~/ssh/JKEY`,
			serviceId: `junghomun00`
		}
	},
	maven: {
		webappsPath: `/usr/share/tomcat9/webapps`
	}
};

// 로깅 함수 -----------------------------------------------------------------------------------
const logger = (type=``, message=``) => {
	const format = (text=``) => text.trim().replace(/^\s+/gm, ``);
	const line = `----------------------------------------`;
	const colors = {
		line: `\x1b[38;5;214m`,
		info: `\x1b[36m`,
		success: `\x1b[32m`,
		warn: `\x1b[33m`,
		error: `\x1b[31m`,
		reset: `\x1b[0m`
	};
	const separator = `${colors.line}${line}${colors.reset}`;

	type === `info` && console.log(format(`
		${separator}
		${colors.info}[INFO]${colors.reset} - ${message}
	`));
	type === `success` && console.log(format(`
		${separator}
		${colors.success}[SUCCESS]${colors.reset} - ${message}
	`));
	type === `warn` && console.log(format(`
		${separator}
		${colors.warn}[WARN]${colors.reset} - ${message}
	`));
	type === `error` && console.log(format(`
		${separator}
		${colors.error}[ERROR]${colors.reset} - ${message}
	`));
};

// OS 확인 --------------------------------------------------------------------------------------
const detectOsAndArgs = () => {
	const winOrLinux = os.platform() === 'win32' ? `win` : `linux`;
	const args = process.argv.slice(2);

	logger(`info`, `운영체제 감지: ${winOrLinux}`);
	logger(`info`, `전달된 인자: ${args.length > 0 ? args.join(', ') : 'none'}`);

	return {
		"os": winOrLinux,
		"args": args
	};
};

// changelog 수정 -------------------------------------------------------------------------------
const modifyChangelog = () => {
	logger(`info`, `changelog.md 업데이트 시작`);

	const currentDate = new Date().toLocaleDateString('ko-KR', {
		"year": 'numeric',
		"month": '2-digit',
		"day": '2-digit'
	});

	const currentTime = new Date().toLocaleTimeString('ko-KR', {
		"hour": '2-digit',
		"minute": '2-digit',
		"second": '2-digit',
		"hour12": false
	});

	const changelog = fs.readFileSync(CONFIG.paths.changelog, 'utf8');
	const versionPattern = /(\s*)(\d+[.]\d+[.]\d+)(\s*)/g;
	const matches = [...changelog.matchAll(versionPattern)];
	const lastMatch = matches[matches.length - 1];
	const lastVersion = lastMatch[2];
	const versionArray = lastVersion.split('.');
	versionArray[2] = (parseInt(versionArray[2]) + 1).toString();

	const newVersion = `\\[ ${versionArray.join('.')} \\]`;
	const formattedDateTime = `- ${currentDate} (${currentTime})`
		.replace(/([.]\s*[(])/g, ` (`)
		.replace(/([.]\s*)/g, `-`)
		.replace(/[(](\W*)(\s*)/g, `(`);

	const newEntry = `\n### ${newVersion}\n\n${formattedDateTime}\n`;
	const updatedChangelog = changelog + newEntry;

	fs.writeFileSync(CONFIG.paths.changelog, updatedChangelog, 'utf8');
	logger(`success`, `changelog.md 업데이트 완료: ${versionArray.join('.')}`);

	return versionArray.join('.');
};

// application.properties 파일 수정 -------------------------------------------------------------
const modifyApplicationProperties = () => {
	logger(`info`, `application.properties 파일 수정 시작`);

	const propertiesFile = fs.readFileSync(CONFIG.paths.properties, 'utf8');
	const lines = propertiesFile.split(/\r?\n/);

	const updatedLines = lines.map(line => (
		line.startsWith('spring.devtools') ? (
			`# ${line}`
		) : line.startsWith('orders-url=http://localhost') ? (
			`orders-url=http://${CONFIG.serverIp}:${CONFIG.localPort}/${CONFIG.projectName}/orders`
		) : (
			line
		)
	));

	const newPropertiesFile = updatedLines.join(os.EOL);
	fs.writeFileSync(CONFIG.paths.properties, newPropertiesFile);

	logger(`success`, `application.properties 파일 수정 완료`);
};

// git push 공통 함수 ---------------------------------------------------------------------------
const gitPush = (remoteName=``, ignoreFilePath=``, winOrLinux=``) => {
	logger(`info`, `Git Push 시작: ${remoteName}`);

	process.chdir(CONFIG.paths.root);

	const ignorePublicFile = fs.readFileSync(CONFIG.paths.gitignorePublic, 'utf8');
	const ignoreContent = fs.readFileSync(ignoreFilePath, 'utf8');
	const currentBranch = execSync(`git branch --show-current`, { "encoding": 'utf8' }).trim();

	logger(`info`, `현재 브랜치: ${currentBranch}`);
	logger(`info`, `.gitignore 파일 교체: ${path.basename(ignoreFilePath)}`);
	fs.writeFileSync(CONFIG.paths.gitignore, ignoreContent, 'utf8');
	execSync(`git rm -r -f --cached .`, { "stdio": 'inherit' });
	execSync(`git add .`, { "stdio": 'inherit' });

	const statusOutput = execSync(`git status --porcelain`, { "encoding": 'utf8' }).trim();

	statusOutput ? (() => {
		logger(`info`, `변경사항 감지 - 커밋 진행`);
		const commitMessage = winOrLinux === `win` ? (
			`git commit -m "%date% %time:~0,8%"`
		) : (
			`git commit -m "$(date +%Y-%m-%d) $(date +%H:%M:%S)"`
		);
		execSync(commitMessage, { "stdio": 'inherit' });
		logger(`success`, `커밋 완료`);
	})() : logger(`info`, `변경사항 없음 - 커밋 건너뜀`);

	logger(`info`, `Push 진행: ${remoteName} ${currentBranch}`);
	execSync(`git push --force ${remoteName} ${currentBranch}`, { "stdio": 'inherit' });
	logger(`success`, `Push 완료: ${remoteName}`);

	fs.writeFileSync(CONFIG.paths.gitignore, ignorePublicFile, 'utf8');
	logger(`info`, `.gitignore 파일 복원`);

	process.chdir(CONFIG.paths.node);
};

// 프로젝트 빌드 -------------------------------------------------------------------------------
const buildProject = () => {
	logger(`info`, `프로젝트 빌드 시작`);

	process.chdir(CONFIG.paths.root);

	try {
		execSync(`mvn clean package`, { "stdio": 'inherit' });
		logger(`success`, `프로젝트 빌드 완료`);
		process.chdir(CONFIG.paths.node);
		return true;
	}
	catch (e) {
		logger(`error`, `프로젝트 빌드 실패`);
		process.chdir(CONFIG.paths.node);
		return false;
	}
};

// gcloud에 업로드 -----------------------------------------------------------------------------
const uploadToGcloud = () => {
	logger(`info`, `gcloud 업로드 시작`);

	const destPath = `gs://${CONFIG.gcs.bucket}/${CONFIG.gcs.path}`;

	try {
		execSync(`gcloud storage cp ${CONFIG.paths.target} ${destPath}`, { "stdio": 'inherit' });
		logger(`success`, `gcloud 업로드 완료`);
		return true;
	}
	catch (e) {
		logger(`error`, `gcloud 업로드 실패`);
		return false;
	}
};

// 원격 서버에서 스크립트 실행 ---------------------------------------------------------------------
const runRemoteScript = (winOrLinux=``) => {
	logger(`info`, `원격 서버 스크립트 실행 시작`);

	const keyPath = winOrLinux === `win` ? (
		CONFIG.ssh.win.keyPath
	) : (
		CONFIG.ssh.linux.keyPath
	);
	const serviceId = winOrLinux === `win` ? (
		CONFIG.ssh.win.serviceId
	) : (
		CONFIG.ssh.linux.serviceId
	);

	const ipAddr = CONFIG.serverIp;
	const webappsPath = CONFIG.maven.webappsPath;
	const projectName = CONFIG.projectName;
	const gcsPath = `gs://${CONFIG.gcs.bucket}/${CONFIG.gcs.path}`;

	const cmdCd = `cd ${webappsPath}`;
	const cmdRm = `sudo rm -rf ${projectName} ${projectName}.war`;
	const cmdCp = `sudo gcloud storage cp ${gcsPath} .`;
	const cmdUnzip = `sudo unzip ${projectName}.war -d ${projectName}`;
	const cmdRestart = `sudo systemctl restart tomcat9`;

	const sshCommand = winOrLinux === `win` ? (
		`powershell -Command "ssh -t -o StrictHostKeyChecking=no -i ${keyPath} ${serviceId}@${ipAddr} '${cmdCd} && ${cmdRm} && ${cmdCp} && ${cmdUnzip} && ${cmdRestart} && exit'"`
	) : (
		`ssh -t -o StrictHostKeyChecking=no -i ${keyPath} ${serviceId}@${ipAddr} '${cmdCd} && ${cmdRm} && ${cmdCp} && ${cmdUnzip} && ${cmdRestart} && exit'`
	);

	logger(`info`, `SSH 명령 실행 중...`);
	execSync(sshCommand, { "stdio": 'inherit' });
	logger(`success`, `원격 서버 스크립트 실행 완료`);
};

// application.properties 파일 복원 -------------------------------------------------------------
const restoreApplicationProperties = () => {
	logger(`info`, `application.properties 파일 복원 시작`);

	const propertiesFile = fs.readFileSync(CONFIG.paths.properties, 'utf8');
	const lines = propertiesFile.split(/\r?\n/);

	const updatedLines = lines.map(line => (
		line.startsWith('# spring.devtools') ? (
			line.substring(2)
		) : line.startsWith('orders-url=http://104.196') ? (
			`orders-url=http://localhost:${CONFIG.localPort}/${CONFIG.projectName}/orders`
		) : (
			line
		)
	));

	const newPropertiesFile = updatedLines.join(os.EOL);
	fs.writeFileSync(CONFIG.paths.properties, newPropertiesFile);

	logger(`success`, `application.properties 파일 복원 완료`);
};

// 실행 ---------------------------------------------------------------------------------------
(() => {
	logger(`info`, `스크립트 실행: gcloud.cjs`);

	try {
		const { os: winOrLinux, args } = detectOsAndArgs();

		args.includes(`--deploy`) ? (() => {
			modifyChangelog();
			modifyApplicationProperties();
			gitPush(`origin`, CONFIG.paths.gitignorePublic, winOrLinux);
			gitPush(`private`, CONFIG.paths.gitignorePrivate, winOrLinux);
			buildProject() ? (() => {
				uploadToGcloud() ? (() => {
					runRemoteScript(winOrLinux);
					restoreApplicationProperties();
					logger(`success`, `전체 배포 프로세스 완료`);
				})() : (() => {
					throw new Error(`gcloud 업로드 실패`);
				})();
			})() : (() => {
				throw new Error(`프로젝트 빌드 실패`);
			})();
		})() : args.includes(`--git`) ? (() => {
			modifyChangelog();
			gitPush(`origin`, CONFIG.paths.gitignorePublic, winOrLinux);
			gitPush(`private`, CONFIG.paths.gitignorePrivate, winOrLinux);
			logger(`success`, `Git Push 완료`);
		})() : (() => {
			throw new Error(`Invalid argument. Use --git or --deploy.`);
		})();

		process.exit(0);
	}
	catch (e) {
		logger(`error`, `스크립트 실행 실패: ${e.message}`);
		process.exit(1);
	}
})();