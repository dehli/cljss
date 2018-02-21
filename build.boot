(set-env!
 :jvm-opts ^:replace ["-Xmx1g" "-server"]
 :resource-paths #{"src" "libs"}
 :dependencies
 '[[org.clojure/clojurescript       "1.9.946"]

   [adzerk/boot-cljs                "2.1.4"          :scope "test"]
   [adzerk/bootlaces                "0.1.13"         :scope "test"]
   [com.cemerick/piggieback         "0.2.2"          :scope "test"]
   [org.clojure/clojure             "1.9.0-RC2"      :scope "test"]
   [org.clojure/tools.nrepl         "0.2.13"         :scope "test"]
   [powerlaces/boot-cljs-devtools   "0.2.0"          :scope "test"]])

(require
 '[adzerk.boot-cljs              :refer [cljs]]
 '[adzerk.bootlaces              :refer :all]
 '[powerlaces.boot-cljs-devtools :refer [cljs-devtools]]
 '[boot.git                      :refer [last-commit]])

(def project 'dehli/cljss)
(def +version+ "0.1.0-SNAPSHOT")
(bootlaces! +version+)

(task-options!
 pom
 {:project     project
  :version     +version+
  :description "Cljs wrapper of JSS."
  :url         "https://github.com/dehli/cljss"
  :scm         {:url "https://github.com/dehli/cljss"}
  :license     {"MIT License"
                "https://github.com/dehli/cljss/blob/master/LICENSE"}}

 push
 {:repo "deploy-clojars"
  :ensure-clean true
  :ensure-tag (last-commit)
  :ensure-version +version+}

 cljs
 {:optimizations :simple
  :compiler-options
  {:main 'cljss.core}})

(defn- build
  []
  (comp (pom)
        (cljs)
        (jar)))

(deftask install-local
  []
  (comp (build)
        (install)))

(deftask deploy-snapshot
  []
  (comp (build)
        (push-snapshot)))

(deftask deploy-release
  []
  (comp (build)
        (push-release)))
