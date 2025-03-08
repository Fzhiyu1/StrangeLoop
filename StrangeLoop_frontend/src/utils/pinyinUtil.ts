import {pinyin} from "pinyin-pro";

export class PinyinUtil {
    // 将中文转换成首字母大写的拼英 eg：小智 XiaoZhi
    public PinYin(indexString:string){
        let strings = pinyin(indexString, {toneType: 'none', type: 'array'});
        let returnString = "";
        for (let index of strings) {
            returnString += this.capitalizeFirstLetter(index)
        }
        return returnString;

    }
    // 字符串首字母大写
    private capitalizeFirstLetter(str:string){
        if (str.length === 0) {
            return str;
        }
        return str.charAt(0).toUpperCase() + str.slice(1);
    }
}
