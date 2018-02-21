(ns cljss.core
  (:require [jss]
            [jss-preset-default]))

(def ^:private jss
  (.getValueByKeys goog.object js/window "jss" "default"))
(def ^:private preset
  (.getValueByKeys goog.object js/window "jssPreset" "default"))

(defn setup
  "Setup JSS. Plugins must be initialized if adding them to setup."
  []
  (.setup jss (preset)))

(defn classes
  [styles]
  (js->clj
   (.-classes
    (.attach
     (.createStyleSheet jss (clj->js styles))))
   :keywordize-keys true))
