(ns aoc-2021.day04
  (:require [aoc-2021.core :as c]
            [clojure.string :as str]))

(defn parse [file]
  (let [[draws & boards] (str/split (c/read-text-file file) #"\n\n")]
    {:draws (->> draws
                 (re-seq #"\d+")
                 (map #(Long/parseLong %)))
     :boards (->> boards
                  (mapcat #(re-seq #"\d+" %)) ;; map and concatenate
                  (map (fn [value] {:marked? false :number (Long/parseLong value)}))
                  (partition 25))})) ;; squares in individual board

(defn bingo? [board]
  (let [rows (partition 5 board)
        cols (apply map vector rows)] ;; transposition of rows
    (->> (concat rows cols)
         (some #(every? :marked? %))
         some?)))

(defn- mark [board draw]
  (for [cell board]
    (update cell :marked? #(or % (= draw (:number cell))))))

(defn- score [board draw]
  (* draw
     (->> board
          (remove :marked?)
          (map :number)
          (reduce + 0))))

(defn lazy-scores [boards [draw & next-draws]]
  (lazy-seq
   (when draw
     (let [marked (map #(mark % draw) boards)
           {finished true playing false} (group-by bingo? marked)]
       (concat (map #(score % draw) finished)
               (lazy-scores playing next-draws))))))

(defn- scores [file]
  (let [{:keys [boards draws]} (parse file)]
    (lazy-scores boards draws)))

(def demo-result (scores "day04_demo"))
(def real-result (scores "day04"))

(first demo-result)
;; => 4512
(last demo-result)
;; => 1924
(first real-result)
;; => 35711
(last real-result)
;; => 5586