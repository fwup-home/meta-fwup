# fwup meta layer

This is a Yocto meta layer for building `fwup` into Yocto projects.

## Dependencies:

This layer depends on: 

URI: https://github.com/openembedded/meta-openembedded.git layers: meta-oe branch: master

## Usage

For `conf/bblayers.conf` you have to add

```text
BBLAYERS ?= " \
   ...
  path_to_source/sources/meta-fwup \
  "
```

There are two main use cases:

One is to add `IMAGE_INSTALL_append = "fwup"` in `conf/local.conf` and run
`bitbake core-image-minimal` to get an image with fwup support.

The second one is to add the following configuration in `conf/local.conf`:

```text
# Add image_types_fwup to the system
IMAGE_CLASSES += "image_types_fwup"

# Declare that we want to make fwup images
IMAGE_FSTYPES = "fwup"

# It's mandatory to define a fwup config file format
FWUP_FILE = "sdimg-raspberrypi0-wifi.fwup"
```

When running `bitbake core-image-minimal` a .fw image will be created at
`tmp/deploy/image/<MACHINE NAME>/core-image-minimal-<MACHINE NAME>.rootfs.fw`
