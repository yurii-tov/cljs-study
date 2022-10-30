(ns example.monkeys
  (:require [reagent.dom :as dom]
            [reagent.core :as r]))


(set! js/document.title "38 🐵")


(def interval (r/atom 1))


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
            [:tr {:key i} (map (fn [i x] [:td {:key i} x]) (range) r)])])


(defn start-monkeying []
  (js/clearTimeout @timeout)
  (when (> @interval 0)
    (reset! timeout (js/setTimeout start-monkeying (/ 1000.0 @interval)))
    (dom/render [monkeys] (js/document.getElementById "monkeys"))))


(defn update-speed [x]
  (reset! interval x)
  (start-monkeying))


(defn input []
  [:div
   {:style {:font-size "0.7em"
            :font-family "Consolas"}}
   "Monkeying speed: "
   [:span {:style {:font-weight 800}} @interval " 🐵/s"]
   [:div
    [:input {:type "range"
             :min 0
             :max 5
             :step 0.2
             :value @interval
             :style {:width "25%"}
             :on-change (fn [x] (-> x .-target .-value update-speed))}]]])


(dom/render [:div
             [:h1 "The 38 Monkeys🐒"]
             [:p {:style {:font-size "0.5em"}} "Арт-объект"]
             [:p#monkeys]
             [:p [input]]]
            (js/document.getElementById "app"))


(start-monkeying)
