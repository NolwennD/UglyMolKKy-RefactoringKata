COMPOSER ?= $(shell which composer)

.PHONY: init
init:
	$(RM) -r .git
	git init

.PHONY: up
up: install-vendor

.PHONY: install-vendor
install-vendor: 
	$(PHP) $(COMPOSER) install

.PHONY: clean
clean: clean-vendor clean-composerlock

.PHONY: clean-vendor
clean-vendor:
	$(RM) -r ./vendor

.PHONY: clean-composerlock
clean-composerlock:
	$(RM) composer.lock
    
.PHONY: unit-tests
unit-tests:
	./vendor/bin/pest

.PHONY: unit-tests-coverage
unit-tests-coverage:
	XDEBUG_MODE=coverage ./vendor/bin/pest --coverage

.PHONY: mutation
mutation:
	./vendor/bin/infection --test-framework=pest --show-mutations

.PHONY: code-sniffer
code-sniffer:
	./vendor/bin/phpcs

.PHONY: code-sniffer-fix
code-sniffer-fix:
	./vendor/bin/phpcbf

