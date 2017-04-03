(defproject datomtest "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 #_[com.datomic/clj-client "0.8.606"]
                 [com.datomic/datomic-free "0.9.5390"]]
  :profiles
  {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
         :source-paths ["src"]}
   :test {:source-paths []}}
  :main datomtest.core)
