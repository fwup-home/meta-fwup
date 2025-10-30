# fwup multi-layer repository for Yocto Project

This repository has three Yocto layers for working with fwup firmware creator tool.

The purpose of each layer is to accommodate different use cases for specific Yocto machine
definitions.

So, this layer provides the following layers:

- meta-fwup: this is the base layer and not linked with any machine. Its purpose is to be a
base layer for fwup usage with Yocto Project

- meta-fwup-qemu: fwup and wic definitions plus bbappend for recipes. This is only needed if your are building qemu machines type

- meta-fwup-raspberry: fwup and wic definitions plus bbappend for recipes. This is only needed if your are building raspberrypi machines type

Please, consult the [meta-fwup README](meta-fwup/README.md) file for more details about fwup integration for Yocto Project.