(ns cljss.core
  (:require ["jss"                :as jss]
            ["jss-preset-default" :as preset]))

(defn- get-default [obj] (.-default obj))

(defn setup
  ([]
   (setup (get-default preset)))
  ([plugins]
   (.setup (get-default jss)
           plugins)))

(defn classes
  [styles]
  (js->clj
   (.-classes
    (.attach
     (.createStyleSheet (get-default jss)
                        (clj->js styles))))
   :keywordize-keys true))
