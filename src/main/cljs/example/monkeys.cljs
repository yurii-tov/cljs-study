(ns example.monkeys
  (:require [reagent.dom :as dom]
            [reagent.core :as r]))


(defn rand-monkey []
  (rand-nth ["๐" "๐" "๐"]))


(def monkeys
  (r/atom (->> rand-monkey
               repeatedly
               (take 38)
               vec)))


(defn monkeys-table []
  [:table
   [:tbody
    (->> (map (comp second list) @monkeys (range))
         (mapcat (fn [i] (list [:td.monkey (@monkeys i)] [:td " "])))
         (partition 15)
         (map (fn [i x] (vec (list* :tr {:key i} x))) (range))
         doall)]])


(defn start-monkeying []
  (dotimes [i (count @monkeys)]
    (letfn [(f []
              (js/setTimeout f (rand-int 10000))
              (swap! monkeys assoc i (rand-monkey)))]
      (f))))


(set! js/document.title "38 ๐ต")


(dom/render [:div
             [:h1#title "The 38 Monkeys๐"]
             [:p {:style {:font-size "0.5em"}} "ะัั-ะพะฑัะตะบั"]
             [:p [monkeys-table]]]
            (js/document.getElementById "app"))


(start-monkeying)
