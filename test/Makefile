ROOT_DIR := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
YOCTO_RELEASE ?= master
YOCTO_DIR ?= /data-work/yocto

ifeq ($(shell test -d "$(YOCTO_DIR)" && echo yes),)
$(error YOCTO_DIR is not a directory or does not exist: $(YOCTO_DIR))
endif

# From https://kas.readthedocs.io/en/latest/command-line.html#environment-variables
export SSTATE_DIR := ${YOCTO_DIR}/sstate-cache/${YOCTO_RELEASE}
export DL_DIR := ${YOCTO_DIR}/downloads
export KAS_BUILD_DIR ?= ${YOCTO_DIR}/builds/${YOCTO_RELEASE}-fwup-demo

KAS ?= kas-container

KAS_CONFIG ?= kas/demos/qemuarm64-uboot.yml
TEST_SUFFIX ?= qemuarm64-uboot

ifeq (${UPDATE}, 1)
	update = --update
else
	update = 
endif

shell-qemux86-64:
	$(MAKE) shell KAS_CONFIG=kas/demos/qemux86-64.yml TEST_SUFFIX=qemux86-64

shell-raspberrypi4-64:
	$(MAKE) shell KAS_CONFIG=kas/demos/raspberrypi4-64.yml TEST_SUFFIX=raspberrypi4-64

shell-qemuarm-uboot:
	$(MAKE) shell KAS_CONFIG=kas/demos/qemuarm-uboot.yml TEST_SUFFIX=qemuarm-uboot

shell-qemuarm64-uboot:
	$(MAKE) shell KAS_CONFIG=kas/demos/qemuarm64-uboot.yml TEST_SUFFIX=qemuarm64-uboot

checkout:
	${KAS} checkout ${KAS_CONFIG}

shell:
	${KAS} --runtime-args \
		"--name fwup-shell-$(TEST_SUFFIX) \
		 --mount src=/dev/kvm,dst=/dev/kvm,type=bind \
		 --mount src=/dev/vhost-net,dst=/dev/vhost-net,type=bind \
		 --device /dev/net/tun:/dev/net/tun \
		 --cap-add=NET_ADMIN \
		 --privileged" \
		shell ${update} ${KAS_CONFIG}

shell-update:
	$(MAKE) shell UPDATE=1

test:
	$(MAKE) -C lux/test
