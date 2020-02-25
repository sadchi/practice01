(ns practice01.app
  (:require
    [components.ui :as u]
    [components.canvas :as c]))

(defn app []
  [:div
   [u/ui-layer]
   [c/canvas]
   ])

