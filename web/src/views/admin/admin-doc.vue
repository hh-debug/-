<template>
  <a-layout>
  <a-layout-content
      :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
  >
    <a-row :gutter="24">
      <a-col :span="8">
        <a-form
            layout="inline"
            :model="param"
        >
          <a-form-item>
            <a-button type="primary" @click="handleQuery()">
              查询
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="add()">
              新增
            </a-button>
          </a-form-item>
        </a-form>
        <a-table
                 v-if="level1.length > 0"
                 :columns="columns"
                 :data-source="level1"
                 :row-key="record => record.id"
                 :loading="loading"
                 :pagination="false"
                 size="small"
                 :default-expand-all-rows="true"
        >
          <template #name="{ text, record}">
            {{record.sort}} {{text}}
          </template>
          <template v-slot:action="{record}">
            <a-space size="small">
              <a-button type="primary" @click="edit(record)" size="small">
                编辑
              </a-button>

              <a-popconfirm
                  title="删除后不可恢复,确认删除?"
                  ok-text="Yes"
                  cancel-text="No"
                  @confirm="handleDelete(record.id)"
              >
                <a-button type="danger" size="small">
                  删除
                </a-button>
              </a-popconfirm>

            </a-space>
          </template>

        </a-table>
      </a-col>
      <a-col :span="16">
        <p>
          <a-form layout="inline" :model="param">
            <a-form-item>
              <a-button type="primary" @click="handleSave()">
                保存
              </a-button>
            </a-form-item>
          </a-form>
        </p>
        <a-form :model="doc" layout="vertical">
          <a-form-item>
            <a-input v-model:value="doc.name" placeholder="名称"/>
          </a-form-item>
          <a-form-item>
            <a-tree-select
                v-model:value="doc.parent"
                style="width: 100%"
                :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                :tree-data="treeSelectData"
                placeholder="请选择父文档"
                tree-default-expand-all
                :replaceFields="{title: 'name', key: 'id', value: 'id'}"
            ></a-tree-select>
          </a-form-item>
          <a-form-item>
            <a-input v-model:value="doc.sort" placeholder="顺序"/>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="handlePreviewContent()">
              <EyeOutlined/>
              内容预览
            </a-button>
          </a-form-item>
          <a-form-item>
            <div id="content"></div>
          </a-form-item>
        </a-form>
      </a-col>

    </a-row>


    <a-drawer width="900" placement="right" :closable="false" :visible="drawerVisible" @close="onDrawerClose">
      <div class="wangeditor" :innerHTML="previewHtml"></div>
    </a-drawer>

  </a-layout-content>
  </a-layout>

<!--  <a-modal-->
<!--    title="文档表单"-->
<!--    v-model:visible="moduleVisible"-->
<!--    :confirm-loading="moduleLoading"-->
<!--    @ok="handleModalOk"-->
<!--    >-->
<!--  </a-modal>-->
</template>
  <script lang="ts">
    import {defineComponent, onMounted, ref  } from 'vue';
    import Axios from "axios";
    import {message} from "ant-design-vue";
    import {Tool} from "@/util/tool";
    import {useRoute} from "vue-router";
    import E from 'wangeditor';


    export default defineComponent({
      name: 'AdminDoc',
      setup() {

        const route = useRoute();
        console.log("路由：", route);
        console.log("route.path：", route.path);
        console.log("route.query：", route.query);
        console.log("route.param：", route.params);
        console.log("route.fullPath：", route.fullPath);
        console.log("route.name：", route.name);
        console.log("route.meta：", route.meta);

        const param = ref();
        param.value = {};
        const docs = ref();
        const loading = ref(false);
        const level1 = ref();
        level1.value = [];

        //因为树选择组件的属性状态，会随当前编辑节点而变化，所以单独声明一个响应式变量
        const treeSelectData = ref();
        treeSelectData.value = [];

        const columns = [
          {
            title: '名称',
            dataIndex: 'name',
            slots: { customRender: 'name' }
          },
          {
            title: 'Action',
            key: 'action',
            slots: { customRender: 'action' }
          }
        ];

        // 数据查询
        const handleQuery = () => {
          loading.value = true;
          //清空现有数据
          level1.value = [];
          Axios.get("/doc/all/" + route.query.ebookId).then((response) => {
            loading.value = false;
            const data = response.data;
            if (data.success) {

              docs.value = data.content;
              console.log("原始数组1:" + docs.value);

              level1.value = [];
              level1.value = Tool.array2Tree(docs.value, 0);
              console.log("树形结构:" + level1.value);

              // 父文档下拉框初始化，相当于点击新增
              treeSelectData.value = Tool.copy(level1.value);
              // 为选择树添加一个"无"
              treeSelectData.value.unshift({id: 0, name: '无'});
            } else {
              message.error(data.message)
            }
          });
        };


        // 表单

        const doc = ref();

        doc.value = {
          ebookId: route.query.ebookId
        };

        const moduleVisible = ref(false);
        const moduleLoading = ref(false);
        // const editor = new E('content');
        // editor.create();
        let editor: any;
        editor = null;

        const initEditor = () => {
          editor = new E('#content') ;
          editor.config.zIndex = 0;
          editor.create();
        };

        /**
         * 将某节点及其子孙节点全部置为disabled
         */
        const setDisable = (treeSelectData: any, id: any) => {
          // console.log(treeSelectData, id);
          // 遍历数组，即遍历某一层节点
          for (let i = 0; i < treeSelectData.length; i++) {
            const node = treeSelectData[i];
            if (node.id === id) {
              // 如果当前节点就是目标节点
              console.log("disabled", node);
              // 将目标节点设置为disabled
              node.disabled = true;

              // 遍历所有子节点，将所有子节点全部都加上disabled
              const children = node.children;
              if (Tool.isNotEmpty(children)) {
                for (let j = 0; j < children.length; j++) {
                  setDisable(children, children[j].id)
                }
              }
            } else {
              // 如果当前节点不是目标节点，则到其子节点再找找看。
              const children = node.children;
              if (Tool.isNotEmpty(children)) {
                setDisable(children, id);
              }
            }
          }
        };

        const ids: Array<string> = [];
        /**
         * 查找整根树枝
         */
        const getDeleteIds = (treeSelectData: any, id: any) => {
          // console.log(treeSelectData, id);
          // 遍历数组，即遍历某一层节点
          for (let i = 0; i < treeSelectData.length; i++) {
            const node = treeSelectData[i];
            if (node.id === id) {
              // 如果当前节点就是目标节点
              console.log("delete", node);
              // 将目标ID放入结果集ids
              // node.disabled = true;
              ids.push(id);

              // 遍历所有子节点
              const children = node.children;
              if (Tool.isNotEmpty(children)) {
                for (let j = 0; j < children.length; j++) {
                  getDeleteIds(children, children[j].id)
                }
              }
            } else {
              // 如果当前节点不是目标节点，则到其子节点再找找看。
              const children = node.children;
              if (Tool.isNotEmpty(children)) {
                getDeleteIds(children, id);
              }
            }
          }
        };

        const handleSave = () => {
          moduleLoading.value = true;
          doc.value.content = editor.txt.html();
          Axios.post("/doc/save",doc.value).then((response) => {
            moduleLoading.value = false;
            const data = response.data;
            if (data.success){
              // moduleVisible.value = false;
              //重新加载列表
              message.success("保存成功！");
              handleQuery();
            }else {
              message.error(data.message)
            }
          });
        };

        /**
         * 内容查询
         **/
        const handleQueryContent = () => {
          Axios.get("/doc/find-content/" + doc.value.id).then((response) => {
            const data = response.data;
            if (data.success) {
              editor.txt.html(data.content)
            } else {
              message.error(data.message);
            }
          });
        };

        //编辑
        const edit = (record: any) => {

          // 清空富文本框
          editor.txt.html("");

          moduleVisible.value = true;
          doc.value = Tool.copy(record);


          handleQueryContent();

          // 不能选择当前节点及其所有子孙节点，作为父节点，会使树断开
          treeSelectData.value = Tool.copy(level1.value);
          setDisable(treeSelectData.value, record.id);

          // 为选择树添加一个"无"
          treeSelectData.value.unshift({id: 0, name: '无'});



        };

        //新增
        const add = () => {

          // 清空富文本框
          editor.txt.html("");

          moduleVisible.value = true;
          doc.value = {
            ebookId: route.query.ebookId
          };

          treeSelectData.value = Tool.copy(level1.value);

          // 为选择树添加一个"无"
          treeSelectData.value.unshift({id: 0, name: '无'});

        };

        //删除
        const handleDelete = (id: number) => {
          getDeleteIds(level1.value, id);
          Axios.post("/doc/delete/" + ids.join(",")).then((response) => {
            const data = response.data;

            if (data.success){
              //重新加载列表
              handleQuery();

            }

          });
        };

        // ----------------富文本预览--------------
        const drawerVisible = ref(false);
        const previewHtml = ref();
        const handlePreviewContent = () => {
          const html = editor.txt.html();
          previewHtml.value = html;
          drawerVisible.value = true;
        };
        const onDrawerClose = () => {
          drawerVisible.value = false;
        };
        onMounted(
            () => {
              handleQuery();
              setTimeout(function (){

                initEditor();
              },100);
            }
        );

        return {
          param,
          // docs,
          level1,
          columns,
          loading,

          edit,
          add,


          moduleVisible,
          moduleLoading,
          handleSave,
          doc,
          handleDelete,
          handleQuery,
          treeSelectData,

          drawerVisible,
          previewHtml,
          handlePreviewContent,
          onDrawerClose,
        }

      }

    });
  </script>



