# fwup meta layer

This is a Yocto meta layer for buiding `fwup` into Yocto projects.
It is really close, but not quite right yet. Since I'm not a Yocto user myself,
I'm not actively working on this. However, it seems close enough to provide a
head start to anyone using Yocto that it's worth sharing.

## Usage

For `conf/bblayers.conf` you have to add

```
BBLAYERS ?= " \
   ...
  path_to_source/sources/meta-fwup \
  "
```

Or add `IMAGE_INSTALL_append = " fwup"` in `conf/local.conf` and run
`bitbake core-image-minimal` to get an image with fwup support.

