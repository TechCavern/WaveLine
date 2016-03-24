/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */

import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  Image,
  ScrollView,
  View
} from 'react-native';
import {
  getTheme
} from 'react-native-material-kit';
class WaveLine extends Component {
  render() {
    var base64Icon = 'http://www.getmdl.io/assets/demos/welcome_card.jpg';
    return (
<ScrollView style={styles.scrollView}>
       <View style={styles.container}>
         <View style={theme.cardStyle}>
           <View
             style={{
               padding : 15,
             }}
             >
             <Text style={[theme.cardContentStyle, {padding:0}]}>
             <Text style={{fontWeight: 'bold'}}>
             Welcome to React Native! {"\n"}
             </Text>
             To get started, edit index.android.js {"\n"}
             Shake or press menu button for dev menu
             </Text>
           </View>
         </View>
       </View>
     </ScrollView>
    );
  }
}
const theme = getTheme();
const styles = require('./resources/styles');


AppRegistry.registerComponent('WaveLine', () => WaveLine);
