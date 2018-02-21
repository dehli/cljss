(set-env!
 :jvm-opts ^:replace ["-Xmx1g" "-server"]
 :resource-paths #{"resources"}
 :dependencies
 '[[org.clojure/clojurescript       "1.9.946"]
   [reagent                         "0.8.0-alpha2"]
   [dehli/cljss                     "0.1.0-SNAPSHOT"]

   [adzerk/boot-cljs                "2.1.4"          :scope "test"]
   [adzerk/boot-cljs-repl           "0.3.3"          :scope "test"]
   [adzerk/boot-reload              "0.5.2"          :scope "test"]
   [com.cemerick/piggieback         "0.2.2"          :scope "test"]
   [org.clojure/clojure             "1.9.0-RC2"      :scope "test"]
   [org.clojure/tools.nrepl         "0.2.13"         :scope "test"]
   [org.slf4j/slf4j-nop             "1.7.21"         :scope "test"]
   [ring/ring-core                  "1.6.2"          :scope "test"]
   [pandeiro/boot-http              "0.8.3"          :scope "test"]
   [weasel                          "0.7.0"          :scope "test"]])

(require
 '[adzerk.boot-cljs              :refer [cljs]]
 '[adzerk.boot-cljs-repl         :refer [cljs-repl]]
 '[adzerk.boot-reload            :refer [reload]]
 '[pandeiro.boot-http            :refer [serve]])

(task-options!
 cljs
 {:optimizations :advanced
  :compiler-options
  {:warnings true
   :pretty-print true}}

 reload
 {:on-jsload 'demo.core/mount-root})

(deftask run
  []
  (comp (serve :not-found 'demo.server/not-found-handler
               :port 8000)
        (watch)
        (cljs-repl)
        (reload)
        (cljs)))
