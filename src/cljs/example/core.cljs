(ns example.core
  (:require ["react"              :as React    :refer  [createElement]]
            ["react-dom"          :as ReactDOM :refer [render]]
            ["jss"                :as Jss]
            ["jss-preset-default" :as Preset]))

(enable-console-print!)

(def jss (.-default Jss))
(def preset (.-default Preset))

;; One time setup w/ default plugins and settings
(.setup jss (preset))

;; These are the styles to be created
(def styles
  {:container {:color :blue}})

(def classes
  (js->clj
   (.-classes
    (.attach
     (.createStyleSheet jss
                        (clj->js styles))))
   :keywordize-keys true))

(defn- component []
  (createElement "div"
                 #js {:className (:container classes)}
                 "Hello, World!"))

(defn mount-root []
  (render (component)
          (.getElementById js/document "app")))

(defn init []
  (mount-root))
