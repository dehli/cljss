(set-env!
 :jvm-opts ^:replace ["-Xmx1g" "-server"]
 :source-paths #{"src/cljs" "src/clj"}
 :resource-paths #{"resources"}
 :dependencies
 '[[org.clojure/clojure        "1.9.0-RC2"]
   [org.clojure/clojurescript  "1.9.946"]

   [adzerk/boot-cljs                "2.1.4"          :scope "test"]
   [adzerk/boot-cljs-repl           "0.3.3"          :scope "test"]
   [adzerk/boot-reload              "0.5.2"          :scope "test"]
   [com.cemerick/piggieback         "0.2.2"          :scope "test"]
   [org.clojure/tools.nrepl         "0.2.13"         :scope "test"]
   [org.slf4j/slf4j-nop             "1.7.21"         :scope "test"]
   [powerlaces/boot-cljs-devtools   "0.2.0"          :scope "test"]
   [ring/ring-core                  "1.6.2"          :scope "test"]
   [pandeiro/boot-http              "0.8.3"          :scope "test"]
   [weasel                          "0.7.0"          :scope "test"]])

(require
 '[adzerk.boot-cljs              :refer [cljs]]
 '[adzerk.boot-cljs-repl         :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload            :refer [reload]]
 '[pandeiro.boot-http            :refer [serve]]
 '[powerlaces.boot-cljs-devtools :refer [cljs-devtools]])

(deftask build
  []
  (comp (speak)
        (cljs)))

(deftask run
  []
  (comp (serve :not-found 'server/not-found-handler)
        (watch)
        (cljs-repl)
        (reload)
        (build)))

(deftask production
  "Sets production options."
  []
  (task-options! cljs {:optimizations :advanced})
  identity)

(deftask development
  "Sets development options."
  []
  (task-options! cljs {:optimizations :none
                       :compiler-options
                       {:warnings true
                        :pretty-print true
                        :install-deps true
                        :npm-deps {:jss "9.3.3"
                                   :jss-preset-default "^4.0.1"
                                   ;; dev-dependencies
                                   :react "16.2.0"
                                   :react-dom "16.2.0"}}}
                 reload {:on-jsload 'example.core/mount-root})
  identity)

(deftask dev
  "Kicks off development process. This includes live reload and file watching."
  []
  (comp (development)
        (run)))
