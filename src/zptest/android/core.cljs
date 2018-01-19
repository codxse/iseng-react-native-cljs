(ns zptest.android.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [zptest.events]
            [zptest.subs]))

(def ReactNative (js/require "react-native"))
(def NativeBase (js/require "native-base"))

(def container (r/adapt-react-class (.-Container NativeBase)))
(def header (r/adapt-react-class (.-Header NativeBase)))
(def left (r/adapt-react-class (.-Left NativeBase)))
(def icon (r/adapt-react-class (.-Icon NativeBase)))
(def app-registry (.-AppRegistry ReactNative))
(def button (r/adapt-react-class (.-Button NativeBase)))
(def body (r/adapt-react-class (.-Body NativeBase)))
(def title (r/adapt-react-class (.-Title NativeBase)))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def web-view (r/adapt-react-class (.-WebView ReactNative)))

(def logo-img (js/require "./images/cljs.png"))

(defn alert [title]
      (.alert (.-Alert ReactNative) title))

(defn app-root []
  (let [greeting (subscribe [:get-greeting])]
    (fn []
      [container
       [header
        [left
         [button {:transparent true}
          [icon {:name "md-menu"
                 :style {:fontSize 20
                         :color "#FFFFFF"}}]]]
        [body
         [title
          {:style {:marginLeft -70}}
          "Gue ganteng"]]]
       [web-view
        {:source {:uri "https://www.tuhh.de/MathJax/test/sample-tex.html"}}]
       [view {:style {:flex-direction "column" :margin 40 :align-items "center"}}
        [button {:light true}
         [text @greeting]]
        [image {:source logo-img
                :style  {:width 80 :height 80 :margin-bottom 30}}]
        [touchable-highlight {:style {:background-color "#999" :padding 10 :border-radius 5}
                              :on-press #(alert "HELLO!")}
         [text {:style {:color "white" :text-align "center" :font-weight "bold"}} "press me"]]]])))

(defn init []
      (dispatch-sync [:initialize-db])
      (.registerComponent app-registry "zptest" #(r/reactify-component app-root)))
