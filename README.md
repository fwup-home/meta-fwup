# fwup meta layer

This is a Yocto meta layer for building `fwup` into Yocto projects.

## Usage

For `conf/bblayers.conf` you have to add

```text
BBLAYERS ?= " \
   ...
  path_to_source/sources/meta-fwup \
  "
```

Or add `IMAGE_INSTALL_append = "fwup"` in `conf/local.conf` and run
`bitbake core-image-minimal` to get an image with fwup support.

