# .bashrc

# User specific aliases and functions
alias cd='cd'
alias CD='cd'
alias rm='rm -rf -i'
alias RM='rm -rf -i'
alias cp='cp -i'
alias CP='cp -i'
alias mv='mv -i'
alias MV='mv -i'
alias ls='ls -a --color=auto'
alias LS='ls -a --color=auto'
alias confN='micro /etc/nginx/nginx.conf'
alias reN='sudo systemctl restart nginx'
alias statN='sudo systemctl status nginx -l'
alias logsN='lnav /var/log/nginx/error.log'
alias confEnv='micro /etc/environment'

# Include hidden files by default in wildcard operations
shopt -s dotglob

# Source global definitions
if [ -f /etc/bashrc ]; then
  . /etc/bashrc
fi

if [ -f /etc/environment ]; then
  . /etc/environment
fi