(ns cljss.core
  (:require ["jss"                :as jss]
            ["jss-preset-default" :as preset]))

(defn- get-default [obj] (.-default obj))

(defn setup
  "Setup JSS. Plugins must be initialized if adding them to setup."
  ([]
   (.setup (get-default jss) ((get-default preset))))
  ([& plugins]
   (apply (aget (get-default jss) "use") plugins)))

(defn classes
  [styles]
  (js->clj
   (.-classes
    (.attach
     (.createStyleSheet (get-default jss)
                        (clj->js styles))))
   :keywordize-keys true))
