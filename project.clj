(defproject practice01 "0.1.0-SNAPSHOT"

  :dependencies [
                 [aleph "0.4.6"]
                 [cheshire "5.10.0"]
                 [com.andrewmcveigh/cljs-time "0.5.2"]
                 [compojure "1.6.1"]
                 [garden "1.3.9"]
                 [hiccup "1.0.5"]
                 [manifold "0.1.6"]
                 [org.apache.logging.log4j/log4j-core "2.12.0"]
                 [org.apache.logging.log4j/log4j-slf4j-impl "2.12.0"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.597"]
                 [org.clojure/core.match "1.0.0"]
                 [org.clojure/tools.logging "0.6.0"]
                 [prismatic/schema "1.1.7"]
                 [reagent "0.9.1"]
                 [ring "1.8.0"]
                 [ring/ring-json "0.5.0"]
                 [thi.ng/geom "1.0.0-RC4"]
                 ]


  :plugins [
            [lein-cljsbuild "1.1.7"]
            [lein-gorilla "0.4.0"]
            ]

  :source-paths ["src" "src/clj" "src/cljs" "src/cljc"]

  :resource-paths ["cfg" "resources"]

  :aot [practice01.core]
  :main practice01.core

  :cljsbuild {:builds
              {:report
               {:jar          true
                :source-paths ["src/cljs" "src/cljc"]
                :compiler     {:output-to     "resources/public/app.js"
                               :output-dir    "resources/public/out"
                               :asset-path    "out"
                               :optimizations :none
                               :pretty-print  false}}}}

  :profiles {:dev
             {:source-paths ["src/cljs" "src/cljc"]
              :cljsbuild    {:builds {:report
                                      {:jar      true
                                       :compiler {:main          "practice01.core"
                                                  :asset-path    "out"
                                                  :optimizations :none
                                                  :pretty-print  true}}}}}
             :prod
             {:cljsbuild {:builds
                          {:report
                           {:jar      true
                            :compiler {:source-map       "resources/public/out.js.map"
                                       :optimizations    :advanced
                                       :closure-warnings {:externs-validation :off
                                                          :non-standard-jsdoc :off}
                                       :pretty-print     false}}}}}}

  :aliases {"init"   ["modules" "install"]
            "bj"     ["cljsbuild" "auto"]
            "bjprod" ["with-profile" "prod" "cljsbuild" "once"]
            "uber"   ["do" "bjprod" ["uberjar"]]}

  )
