Vagrant.configure("2") do |config|

    config.vm.box = "ubuntu_library_db"
    # config.vm.box_url = "https://cloud-images.ubuntu.com/xenial/current/xenial-server-cloudimg-i386-vagrant.box"
    config.vm.box_url = "https://cloud-images.ubuntu.com/cosmic/current/cosmic-server-cloudimg-amd64-vagrant.box"
   	config.vm.host_name = "ubuntu-library-db"
   
    config.vm.hostname = "ubuntu-library-db"
    config.vm.network :private_network, ip: "192.168.50.50"
    config.vm.provision :shell, :path => "installation.sh"
	config.vm.network "forwarded_port", guest: 80, host: 8080
	
    
end