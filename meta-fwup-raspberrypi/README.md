# fwup definition layer for raspberrypi machines

This is a Yocto meta layer for raspberrypi machines. It provides fwup configuration for the following
machines:

- raspberry0-wifi
- raspberry4-64
- raspberry5

Also a standard wic definition for generate fwup firmware image.

## Dependencies:

This layer depends on: 

* URI: https://github.com/fwup-home/meta-fwup layers: meta-fwup
* URI: https://github.com/agherzan/meta-raspberrypi layers: meta-raspberrypi

## Patches

Please submit any patches for meta-fwup-raspberrypi to the github issue tracker.

Maintainer: João Henrique Ferreira de Freitas `<joaohf@gmail.com>`