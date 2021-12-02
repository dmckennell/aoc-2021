(ns aoc-2021.core
  (:require [clojure.java.io :as io]))

(defn read-text-file [filename]
  (slurp (io/resource (str filename ".txt"))))