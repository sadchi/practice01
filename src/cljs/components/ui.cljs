(ns components.ui
  (:require
    [components.common-styles :as cs]
    [components.core :as c]
    [components.params :as p]
    [garden.color :as gc]
    [garden.core :refer [css]]
    [garden.units :refer [px]]
    [reagent.core :as r]
    ))

(def params
  {:padding {:left 16}})

(defn v [& k] (get-in params k))

(def ui-wrapper ^:css {:position "fixed"
                       :top      0
                       :bottom   0
                       :left     0
                       :right    0
                       :z-index  (:med p/z-index)})


(defn ui-layer []
  [:div (c/cls 'ui-wrapper
               'cs/content-font)
   "ui-layer"])

(c/add-css (ns-interns 'components.ui))