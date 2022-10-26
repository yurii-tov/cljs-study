(require '[cljs.main :as main]
         '[clojure.string :as cstr]
         '[clojure.java.io :as io])


(def output-dir "target/out")


(defn compile-cljs [ns]
  (let [output-html (str output-dir "/" (str ns ".html"))
        options (concat *command-line-args*
                        ["--output-dir" output-dir
                         "--output-to" (str output-dir "/" ns ".js")
                         "-c" ns])]
    (println "Compiling using options:" options "->" output-html)
    (apply main/-main options)
    (spit output-html
          (-> (io/resource "index.html")
              slurp
              (cstr/replace "${script}" (str ns ".js")))
          :encoding "utf-8")))


(compile-cljs "example.core")
