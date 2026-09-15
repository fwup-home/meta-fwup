# meta-fwup tests

In order to exercise and verify if meta-fwup works with Yocto Project,
some functional integration have been created. These tests are written
using a tool called [Lux \(LUcid eXpect scripting\)](https://github.com/hawk/lux).

The purpose is to add test cases covering common workflows like creating a .fw
and updating a target QEMU instance. It is also a good way to document how to make .fw
images and what configurations are needed.

## Folder layout

* _kas_, [KAS](https://kas.readthedocs.io/) configuration fragments
* _lux_
  * _support_, macros and lux global configurations
  * _test_, test cases written in [lux](https://github.com/hawk/lux)

## Test execution

For test execution the required tools are:

* kas, [KAS Getting Started](https://kas.readthedocs.io/en/latest/userguide/getting-started.html)
* lux, [Lux Installation](https://github.com/hawk/lux/blob/master/doc/lux.md#../INSTALL)
* QEMU scripts provided by Yocto Project setup

Test scripts and all its dependencies live at _lux/test_ folder.

To run all tests:

```bash
cd lux/test
make
```

The variable `LUX_FILES` could be used to select and running a single test. E.g.:

```bash
make test LUX_FILES=fwup-a-b-update.lux
```

The entrypoint Makefile at _test/Makefile_ is the one which controls two environment variables used by KAS:

- _YOCTO\_DIR_, points to a folder used to instantiate yocto build files
- _KAS_, points to kas binary. By default it is defined as `kas-container`

## Development

The root Makefile has some rules for easy setup a kas shell and starting some exploration. That Makefile is also called by test scripts for setting Yocto environment.