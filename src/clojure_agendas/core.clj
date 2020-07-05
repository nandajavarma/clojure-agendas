(ns clojure-agendas.core
  (:require [ring.adapter.jetty :as webserver]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]]))


(defn welcome
  [request]
  {:status 200
   :headers {}
   :body "Hello, World"}
   )

(defn hello
  [request]
  (let [name (get-in request [:route-params :name])]
  {:body (str "Hello, " name)
   :status 200
   :headers {}}))

(defn about
  [request]
  {:status 200
   :headers {}
   :body "This is Nandaja trying to learn clojure"})

(defroutes app
  (GET "/" [] welcome)
  (GET "/hello/:name" [name] hello)
  (GET "/about" [] about)
  (GET "/request-info" [] handle-dump)
  (not-found "Endpoint not found"))

(defn -dev-main
  "This is the dev endpoint"
  [port-number]
  (webserver/run-jetty
   (wrap-reload #'app)
   {:port (Integer. port-number)
    :join? false}
   ))

(defn -main
  [port-number]
  (webserver/run-jetty
   app
   {:port (Integer. port-number)}
  ))
