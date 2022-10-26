(ns example.core
  (:require [reagent.dom :as dom]
            [example.math :as m]))


(dom/render [:div
             [:p
              [:table#food-chains
               {:style {:border "2px solid green"}}
               (for [[a b c d] [["Rabbits" "carrots" "🐰" "🥕"]
                                ["Wolves" "rabbits" "🐺" "🐰"]
                                ["Humans" "everything" "👦" "🐴🐰🥕🐮"]]]
                 [:tr
                  [:td (str a " eat " b)]
                  [:td c]
                  [:td {:style {:border "1px solid green"
                                :padding 0}}]
                  [:td d]])]]
             [:p (let [x (rand-int 10)]
                   (str "By the way, factorial of " x " is " (m/fac x)))]]
            (js/document.getElementById "app"))
