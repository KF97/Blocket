
VirtualBox 설치
https://www.virtualbox.org/wiki/Downloads

Vagrant 설치
https://www.vagrantup.com/downloads

설치 여부 확인
vagrant version 

호스트,가상머신 사이의 플러그인 설치
vagrant plugin install vagrant-scp

초기화
vagrant init 

Vagrantfile 내용 수정

  ```vim
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

가상 머신 구동 명령어 실행
vagrant up

구동 상태 확인
vagrant status

가상 머신 접속(eth0)
vagrant ssh eth0