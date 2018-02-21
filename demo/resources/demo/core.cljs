(ns demo.core
  (:require [reagent.core :as reagent]
            [cljss.core   :as cljss]))

(cljss/setup)

(def styles {:container {:color :blue
                         :font-family :papyrus}
             "@media (max-width: 800px)"
             {:container {:color :red}}})

(defn- component []
  (let [classes (cljss/classes styles)]
    [:div {:className (:container classes)}
     "Hello, World!"]))

(defn mount-root []
  (reagent/render [component]
    (.getElementById js/document "reagent-app")))

(defn init []
  (mount-root))
