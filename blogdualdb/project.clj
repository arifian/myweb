(defproject webtest "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]

                 ;;web
                 
                 [io.pedestal/pedestal.service "0.5.1"]
                 [io.pedestal/pedestal.route   "0.5.1"]
                 [io.pedestal/pedestal.jetty   "0.5.1"]
                 [org.slf4j/slf4j-simple       "1.7.21"]
                 [com.datomic/datomic-free "0.9.5390"]
                 [hiccup "1.0.5"]
                 [com.stuartsierra/component "0.3.2"]]
  :main webtest.core
  :source-paths ["src" "config"]
  :repl-options {:init-ns user}
  #_:plugins #_[[lein-cloverage "1.0.7-SNAPSHOT"]
            [lein-cljsbuild "1.1.4"]]
  :jvm-opts ["-Xms256M" "-Xmx512M" "-server" "-XX:-OmitStackTraceInFastThrow"]
  :profiles
  {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
         :source-paths ["dev" "config"]}
   :test {:source-paths []}}
  :target-path "target/%s")
