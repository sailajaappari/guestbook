(ns guestbook.pages.guestlist
  (:require [reagent.session :as session]
            [clojure.string :as s]
            [reagent.core :as reagent :refer [atom]]
            [secretary.core :refer [dispatch!]]))

(defn guest-list-page []
  [:div
   [:div [:h2 "Guests"]]
   (for [{:keys [first-name last-name]}
         (session/get :guests)]
     [:div
      [:p first-name " " last-name]])
   [:button {:type "submit"
             :on-click #(dispatch! "/sign-in")} "sign in"]])
