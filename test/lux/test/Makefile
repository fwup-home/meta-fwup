.PHONY: test

LUX_OPTIONS +=
LUX_FILES ?= \
    build-fwup-images.lux \
	fwup-a-b-update.lux

.PHONY: help
.DEFAULT_GOAL := help

all: test

test: ## Run lux test scripts. LUX_FILES variable can be used to run specific scripts.
	lux --config_dir ../support/luxcfg --progress=brief $(LUX_OPTIONS) $(LUX_FILES)

rerun: ## Rerun lux test scripts.
	$(MAKE) test LUX_OPTIONS="--rerun=fail"

clean: ## Clean lux logs.
	rm -rf lux_logs

help: ## Display this help message.
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n\nTargets:\n"} /^[a-zA-Z0-9_-]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 }' $(MAKEFILE_LIST)
