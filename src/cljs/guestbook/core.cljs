(ns guestbook.core
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [guestbook.ajax :refer [load-interceptors!]]
            [guestbook.pages.guestlist
            :refer [guest-list-page]]
           [guestbook.pages.guest :refer [guest-page]])
  (:import goog.History))

(defn page []
  [(session/get :current-page)])

;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :current-page guest-list-page))

(secretary/defroute "/sign-in" []
  (session/put! :current-page guest-page))


;; Must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
        (events/listen
          EventType/NAVIGATE
          (fn [event]
             (secretary/dispatch! (.-token event))))
        (.setEnabled true)))

;; Initialize app
(defn mount-components []
  (r/render [#'page] (js/document.getElementById "app")))

(defn init []
  (load-interceptors!)
  (hook-browser-navigation!)
  (mount-components))

(init)
