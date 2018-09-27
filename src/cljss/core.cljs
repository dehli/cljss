(ns cljss.core
  (:require [goog.object :as gobj]
            [jss]
            [jss-preset-default]))

(def ^:private jss
  (gobj/getValueByKeys js/window "jss" "default"))
(def ^:private preset
  (gobj/getValueByKeys js/window "jssPreset" "default"))

(defn setup []
  (js-invoke jss "setup" (preset)))

(defn classes [styles]
  (-> jss
      (js-invoke "createStyleSheet" (clj->js styles))
      (js-invoke "attach")
      (gobj/get "classes")
      (js->clj :keywordize-keys true)))
