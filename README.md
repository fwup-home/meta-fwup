# fwup meta layer

This is a Yocto meta layer for building [fwup](https://github.com/fwup-home/fwup) into Yocto projects.

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

## Firmware authentication

fwup is able to create [firmware authenticated](https://github.com/fwup-home/fwup?tab=readme-ov-file#firmware-authentication)
archives. It's also possible to sign firmware archives generated by image_types_fwup image class.
The following configuration can be added into `local.conf` configuration:

```
# Enable fwup sign
FWUP_SIGN_ENABLE = "1"

# Private key file location
FWUP_PRIVATE_KEY_FILE = "/data-work/yocto/work/fwup/build/fwup-key.priv"

# Public key file location
FWUP_PUBLIC_KEY_FILE = "/data-work/yocto/work/fwup/build/fwup-key.pub"
```

The variable `FWUP_SIGN_ENABLE` enables the code responsible for calling fwup command
with the right parameters. Also the variables `FWUP_PRIVATE_KEY_FILE` and `FWUP_PUBLIC_KEY_FILE`
have to point to the correct location of private and public key files.

One way to create a key pair is using the command `fwup -g`. However, others methods also exist.