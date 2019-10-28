Vagrant.configure("2") do |config|

    config.vm.box = "ubuntu/trusty64"
    config.vm.hostname = "ubuntu-library-db"
    config.vm.network :private_network, ip: "192.168.50.50"
    config.vm.provision :shell, :path => "installation.sh"
    config.vm.network "forwarded_port", guest: 80, host: 8080

end