(ns practice01.core
  (:require
    [aleph.http :as http]
    [cheshire.core :refer [generate-string]]
    [clojure.string :as str]
    [clojure.tools.logging :as log]
    [compojure.core :as c]
    [compojure.route :as cr]
    [hiccup.page :refer [html5 include-js]]
    [practice01.cfg :as cfg]
    [ring.middleware.json :refer [wrap-json-response wrap-json-body wrap-json-params]]
    [ring.middleware.keyword-params :refer [wrap-keyword-params]]
    [ring.middleware.params :as params]
    [ring.middleware.params :refer [wrap-params]]
    [ring.middleware.reload :refer [wrap-reload]]
    ))

(defn log-exception [^Throwable e]
  (log/error (str (.getName (.getClass e)) ":") (.getMessage e))
  (->> (.getStackTrace e)
       (map #(str "  at " %))
    (map #(log/error %))
    doall))


(defn generate-exception [^Throwable e]
  {:exception (str (.getName (.getClass e)) ":" (.getMessage e))
   :trace     (str/join (map #(str "\n  at " %) (.getStackTrace e)))})

(defn wrap-exception-handling [handler]
  (fn [request]
    (try
      (handler request)
      (catch Throwable e
        (log/trace (generate-string (assoc (:json-params request) :exception (generate-exception e))))
        (log-exception e)
        (throw e)))))

(defn common-handler [handler]
  (-> handler
      wrap-exception-handling
      wrap-keyword-params
      wrap-params
      wrap-json-params
      wrap-json-response
      ))

(defn handlers [cfg]
  (params/wrap-params
    (c/routes
      (c/GET "/" [] {:status  200
                     :headers {"Content-type" "text/html"}
                     :body    (html5
                                [:head
                                 [:title "Practice01"]]
                                [:body
                                 [:div#app "Test app"]
                                 (include-js "app.js")])})
      (cr/resources "/")
      (cr/not-found "No such page."))))



(defn -main [& args]
  (let [cfg (cfg/read-cfg "./cfg/config.clj")]
    (log/info (apply str (take 200 (repeat "*"))))
    (log/debug "Starting with cfg: " cfg)
    (http/start-server (wrap-reload (handlers cfg)) (:server cfg))
    (println "Started")))



