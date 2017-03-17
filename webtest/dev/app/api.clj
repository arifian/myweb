(ns app.api
  (:require [io.pedestal.interceptor :refer [interceptor]]
            [app.template :as mold]))

(def database (atom {:test1 {:number 1 :title "Lorem Ipsum #1" :content "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vel consequat augue. Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus feugiat venenatis elit, sed elementum quam ullamcorper vel. Sed laoreet tincidunt mauris vitae pharetra. Maecenas sed risus non augue sagittis scelerisque quis vel libero. Sed pharetra malesuada condimentum. Aliquam laoreet, metus ut cursus molestie, ante lectus finibus ex, ut pretium orci eros eget orci. Maecenas quis felis arcu. Proin ac eros velit. Proin ut egestas tellus, id consectetur libero. Donec dignissim, est id vulputate bibendum, ipsum quam faucibus odio, sit amet placerat urna ipsum id ex. Proin sodales augue vel porta dignissim. Duis velit ante, tincidunt ac nibh ut, tincidunt sollicitudin lorem. Integer purus sem, consequat faucibus risus in, tempus venenatis enim. Nunc ut luctus nisi."}
                     :test2 {:number 2 :title "Lorem Ipsum #2" :content "Nunc eget tempus diam, ut ultricies justo. Nullam consectetur quam ac ex vehicula sodales. Nullam vehicula eget purus ut pellentesque. Vestibulum ultrices massa quis augue volutpat, a scelerisque purus finibus. Quisque nunc tellus, posuere ut ipsum nec, euismod fermentum arcu. Cras rutrum sed ipsum at volutpat. Nunc imperdiet, lectus in cursus eleifend, nulla eros dignissim quam, at tempor augue velit sit amet lacus. Nunc id purus augue. Quisque in magna dapibus, consectetur metus ac, feugiat elit. Nunc eu ligula ut tellus rhoncus mollis. Etiam faucibus eu massa quis laoreet."}
                     :test3 {:number 3 :title "Lorem Ipsum #3" :content "Maecenas facilisis feugiat metus vitae mollis. Donec sit amet mauris scelerisque, mattis arcu et, vulputate nisl. Sed nec placerat sem. Vivamus in dictum eros, nec porta est. Cras porta finibus blandit. Vestibulum turpis ante, rutrum porttitor est ac, vulputate vulputate nisl. Nam dictum nunc ac leo aliquam pellentesque. Fusce mollis sed mi vitae egestas. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vivamus sit amet orci dictum, laoreet augue vel, malesuada est. In laoreet justo sit amet neque faucibus dapibus. Phasellus et magna malesuada, elementum enim in, imperdiet est. Mauris porttitor dui dolor, nec eleifend turpis hendrerit aliquet."}}))

(def home-main
  (interceptor
   {:name :home-main
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/home-html @database)}]
        (assoc context :response response)))}))

(def about-main
  (interceptor
   {:name :about
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body mold/about-html}]
        (assoc context :response response)))}))
