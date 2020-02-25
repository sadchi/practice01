(ns practice01.core
  (:require
    [reagent.core :as r]
    [practice01.app :as a]
    ))

(r/render-component [a/app] (.getElementById js/document "app"))
