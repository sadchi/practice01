(ns components.canvas
  (:require
    [components.common-styles :as cs]
    [components.core :as c]
    [components.params :as p]
    [reagent.core :as r]
    ))


(def params
  {:padding {:left 16}})

(defn v [& k] (get-in params k))

(def canvas-wrapper ^:css {:position "fixed"
                           :top      0
                           :bottom   0
                           :left     0
                           :right    0
                           :z-index  (:low p/z-index)})


(def context-default-attribs
  {:alpha                                true
   :antialias                            true
   :depth                                true
   :fail-if-major-performance-caveat     false
   :prefer-low-power-to-high-performance false
   :premultiplied-alpha                  true
   :preserve-drawing-buffer              false
   :stencil                              false})


(def gl-ctx (atom nil))

(defn mk-gl-ctx [canvas-id]
  (let [canvas (.getElementById js/document canvas-id)
        attribs (clj->js context-default-attribs)]
    (reduce (fn [id]
              (try
                (let [ctx (.getContext canvas id attribs)]
                  (set! (.-onselectstart canvas) (constantly false))
                  (when ctx (reduced ctx)))
                (catch js/Error e))) ["webgl" "experimental-webgl" "webkit-3d" "moz-webgl"])))

(defn canvas []
  (let []
    (r/create-class
      {:component-did-mount (fn [_]
                              (reset! gl-ctx (mk-gl-ctx "main-canvas"))
                              (doto @gl-ctx
                                (.clearColor 0.75 0.85 0.8 1)
                                (.clearDepth 1)
                                (.clear (bit-or 0x100 0x4000))))
       :reagent-render      (fn []
                              [:canvas#main-canvas
                               (c/cls 'canvas-wrapper
                                      :width (.-innerWidth js/window)
                                      :height (.-innerHeight js/window))])})))

(c/add-css (ns-interns 'components.canvas))
