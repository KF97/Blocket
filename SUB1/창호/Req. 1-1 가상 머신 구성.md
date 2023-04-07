# Sub PJT I (블록체인)

- 스켈레톤 프로젝트
    - 개발 환경 구성

        1) 스켈레톤 프로젝트 내려받기

        1. 원하는 위치에 스켈레톤 프로젝트 다운로드

        ```jsx
        git clone https://lab.ssafy.com/s05-blockchain/skeleton-project.git
        ```

        1. Remote Repository 정보를 본인의 Repository로 변경

        ```jsx
        git remote set-url origin https://lab.ssafy.com/s05-blockchain/S05P21B101.git
        git remote -v
        ```

        2) 가상 머신 구성

        1. VirtualBox 설치
        - 공식 웹사이트에서 OS 버전에 맞는 설치 파일 다운로드
        - Custom Setup의 기본값 그대로 설치 진행
        - 설치 완료 단계에서 Start Oracle VM VirtualBox after installation 체크, VirtualBox 실행 화면 및 버전 확인
        1. Vagrant 설치
        - 공식 웹사이트에서 OS 버전에 맞는 설치 파일 다운로드
        - 설치 완료 후 설치 프로그램 요구에 따라 필요 시 OS 재부팅
        - 설치 여부 및 버전 확인 `Vagrant version`
        - 호스트와 가상 머신 간 파일 전송 플러그인 설치 `vagrant plugin install vagrant-scp`
        1. 가상 머신 생성 및 구동
        - 원하는 작업 디렉토리에서 Vagrant 초기화 (설정 파일 생성) `vagrant init`
        - 생성된 Vagrantfile의 내용 수정

        ```ruby
        # -*- mode: ruby -*-
        # vi: set ft=ruby :

        # All Vagrant configuration is done below. The "2" in Vagrant.configure
        # configures the configuration version (we support older styles for
        # backwards compatibility). Please don't change it unless you know what
        # you're doing.

        VAGRANT_API_VERSION = "2"

        vms = {
          'eth0' => '10',
          'eth1' => '11'
        }

        Vagrant.configure(VAGRANT_API_VERSION) do |config|
           config.vm.box = "ubuntu/bionic64"
           vms.each do |key, value|
              config.vm.define "#{key}" do |node|
                 node.vm.network "private_network", ip: "192.168.50.#{value}"
                 if "#{key}" == "eth0"
                    node.vm.network "forwarded_port", guest: 8545, host: 8545
                 end
                 node.vm.hostname = "#{key}"
                 node.vm.provider "virtualbox" do |nodev|
                 nodev.memory = 2048
                 end
              end
           end
        end
        ```

        - 가상 머신 구동 명령어 실행 `vagrant up`
        - 가상 머신 구동 상태 확인 `vagrant status`
        - 가상 머신 접속 (eth0 기준) `vagrant ssh eth0`
            - 계정 패스워드 설정 및 sshd_config 수정 시 ,IP를 통한 SSH로도 접속 가능