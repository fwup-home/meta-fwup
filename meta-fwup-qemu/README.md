# fwup definition layer for qemu machines

This is a Yocto meta layer for qemu machines. It provides fwup configuration for the following
machines:

- qemuarm-uboot
- qemuarm64-uboot

Also a standard wic definition for generate fwup firmware image.

## Dependencies:

This layer depends on: 

* URI: https://github.com/fwup-home/meta-fwup layers: meta-fwup
* URI: https://github.com/meta-erlang/meta-qemu-bsp layers: meta-qemu-bsp

## Patches

Please submit any patches for meta-fwup-qemu to the github issue tracker.

Maintainer: João Henrique Ferreira de Freitas `<joaohf@gmail.com>`