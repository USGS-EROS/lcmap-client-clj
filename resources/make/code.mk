JS_REPL_ID = "js-repl"
JS_REPL_HTTP_PORT = 9001

build: clean
	@lein compile
	@lein uberjar

standalone: build
	java -jar $(STANDALONE)

standalone-heavy: build
	java -Xms3072m -Xmx3072m -jar $(STANDALONE)

shell:
	@lein repl

repl:
	@lein repl

js:
	@lein cljsbuild once dev

simpleton:
	-@lein simpleton $(JS_REPL_HTTP_PORT) file :from dev-resources/html/dev.html &

repl-js: simpleton
	@lein trampoline cljsbuild repl-launch $(JS_REPL_ID)

clean-all: clean clean-docs clean-docker

clean:
	@rm -rf target
	@rm -f pom.xml

deps-tree:
	@lein pom
	@mvn dependency:tree

loc:
	@find src -name "*.clj" -exec cat {} \;|wc -l

check:
	@lein with-profile +testing,-dev test

kibit:
	@lein with-profile +testing,-dev kibit

eastwood:
	@lein with-profile +testing,-dev eastwood "{:namespaces [:source-paths]}"

lint: kibit eastwood

lint-unused:
	@lein with-profile +testing,-dev eastwood "{:linters [:unused-fn-args :unused-locals :unused-namespaces :unused-private-vars :wrong-ns-form] :namespaces [:source-paths]}"

lint-ns:
	@lein with-profile +testing,-dev eastwood "{:linters [:unused-namespaces :wrong-ns-form] :namespaces [:source-paths]}"

run:
	-@lein trampoline run

test-auth-server:
	@cd test/support/auth-server && lein with-profile +dev run
