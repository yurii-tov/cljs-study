(ns example.monkeys
  (:require [reagent.dom :as dom]
            [reagent.core :as r]))


(set! js/document.title "38 🐵")


(def interval (r/atom 0.5))


(def timeout (atom nil))


(defn monkeys []
  [:table (for [[i r] (->> (partial rand-nth ["🙈" "🙉" "🙊"])
                           repeatedly
                           (mapcat (comp reverse vector) (repeat " "))
                           (take 30)
                           (partition 15)
                           cycle
                           (take 5)
                           (map vector (range)))]
            [:tr {:key i} (map (fn [x] [:td x]) r)])])


(defn render-monkeys []
  (reset! timeout (js/setTimeout render-monkeys (* 1000 @interval)))
  (dom/render [monkeys] (js/document.getElementById "monkeys")))


(defn update-params [event]
  (let [v (.. event -target -value)]
    (reset! interval v)
    (js/clearTimeout @timeout)
    (render-monkeys)))


(defn input []
  [:div
   [:span
    {:style {:font-size "0.7em"
             :font-family "Consolas"}}
    "Monkeying speed: " [:input {:type "range"
                                 :min 0.2
                                 :max 5
                                 :step 0.2
                                 :value @interval
                                 :style {:width "25%"}
                                 :on-change update-params}]]])


(dom/render [:div
             [:h1 "The 38 Monkeys🐒"]
             [:p {:style {:font-size "0.5em"}} "Арт-объект"]
             [:p [input]]
             [:p#monkeys]]
            (js/document.getElementById "app"))


(render-monkeys)
