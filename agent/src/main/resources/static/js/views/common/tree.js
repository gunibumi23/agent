const Trees = function(params) {
  const list = params.list;
  this.listToTree = (list) => {
      const map  = {};
      const tree = [];
      // Create a map of id to objects
      list.forEach(item => {
          map[item.id] = { ...item, children: [] };
      });
  
      // Link children to their parent nodes
      list.forEach(item => {
          if (item.parentId !== null && map[item.parentId]) {
              map[item.parentId].children.push(map[item.id]);
          } else {
              tree.push(map[item.id]);
          }
      });  
      return tree;    
  };  
  this.treeList = this.listToTree(list);
  
  return this;
}