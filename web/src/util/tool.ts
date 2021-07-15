export class Tool {
  /**
   * 空校验 null或""都返回true
   */
  public static isEmpty (obj: any) {
    if ((typeof obj === 'string')) {
      return !obj || obj.replace(/\s+/g, "") === ""
    } else {
      return (!obj || JSON.stringify(obj) === "{}" || obj.length === 0);
    }
  }

  /**
   * 非空校验
   */
  public static isNotEmpty (obj: any) {
    return !this.isEmpty(obj);
  }

  /**
   * 对象复制
   * @param obj
   */
  public static copy (obj: object) {
    if (Tool.isNotEmpty(obj)) {
      return JSON.parse(JSON.stringify(obj));
    }
  }

  /**
   * 使用递归将数组转为树形结构
   * 父ID属性为parent
   */
  public static array2Tree (array: any, parentId: number) {
    if (Tool.isEmpty(array)) {//判断传进的分类是否为空
      return [];
    }

    const result = [];//初始化结果集
    for (let i = 0; i < array.length; i++) {//遍历分类
      const c = array[i];//c为每一个分类
      // console.log(Number(c.parent), Number(parentId));
      if (Number(c.parent) === Number(parentId)) {//判断每一个分类的节点是否等于0或递归进来的当前分类的子节点
        result.push(c);//push到一级节点

        // 递归查看当前节点对应的子节点
        const children = Tool.array2Tree(array, c.id);//传递当前分类的id
        if (Tool.isNotEmpty(children)) {//判断当前
          c.children = children;
        }
      }
    }
    return result;
  }

  /**
   * websocket 增加的方法
   * 随机生成[len]长度的[radix]进制数
   * @param len
   * @param radix 默认62
   * @returns {string}
   */
  public static uuid (len: number, radix = 62) {
    const chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    const uuid = [];
    radix = radix || chars.length;

    for (let i = 0; i < len; i++) {
      uuid[i] = chars[0 | Math.random() * radix];
    }

    return uuid.join('');
  }
}
