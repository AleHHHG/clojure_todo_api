(ns todo.handler
  (:require [compojure.core :refer :all]
     [compojure.handler :as handler]
     [compojure.route :as route]
     [ring.middleware.json :as json]
     [ring.util.response :refer [response]]
     [todo.query :refer :all]))

(defroutes app-routes
  (GET "/api/todos" []
       (response (get-todos)))

  (GET "/api/todos/:id" [id]
       (response (get-todo (Integer/parseInt id))))

  (POST "/api/todos" {:keys [params]}
    (let [{:keys [title description]} params]
      (response (add-todo title description))))

  (PUT "/api/todos/:id" [id title description]
       (do
          (update-todo (Integer/parseInt id) title description)
          (response (get-todo (Integer/parseInt id)))))

  (PUT "/api/todos/:id/complete" [id]
       (do
          (complete-todo (Integer/parseInt id))
          (response (get-todo (Integer/parseInt id)))))

  (DELETE "/api/todos/:id" [id]
        (do
          (delete-todo (Integer/parseInt id))
          (response { :message "Deleted success"} )))

  (route/resources "/")

  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (json/wrap-json-params)
      (json/wrap-json-response)))
