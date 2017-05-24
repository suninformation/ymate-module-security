/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.module.security;

import net.ymate.module.security.annotation.Components;
import net.ymate.module.security.annotation.Menu;
import net.ymate.module.security.annotation.MenuGroup;
import net.ymate.module.security.annotation.SubMenu;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 组件元数据
 *
 * @author 刘镇 (suninformation@163.com) on 17/5/20 下午2:10
 * @version 1.0
 */
public class ComponentsMeta extends AbstractComponents {

    private static Map<String, ComponentsMeta> __COMPONENTS_CACHES;

    static {
        __COMPONENTS_CACHES = new ConcurrentHashMap<String, ComponentsMeta>();
    }

    private MenuNodeGroup[] menuGroups;

    private MenuNode[] menus;

    public static ComponentsMeta createIfNeed(Class<?> targetClass) throws Exception {
        Components _comp = targetClass.getAnnotation(Components.class);
        if (_comp != null) {
            String _id = StringUtils.defaultIfBlank(_comp.id(), targetClass.getName());
            //
            ComponentsMeta _meta = __COMPONENTS_CACHES.get(_id);
            if (_meta == null) {
                _meta = new ComponentsMeta();
                _meta.setId(_id);
                _meta.setName(__doTryLoadI18N(_comp, _comp.name()));
                _meta.setIcon(__doTryLoadI18N(_comp, _comp.icon()));
                _meta.setMapping(_comp.mapping());
                _meta.setDisplay(_comp.displayType().equals(ISecurity.DisplayType.SHOW));
                _meta.setOrder(_comp.order().value());
                _meta.setPermission(_comp.permission());
                // @MenuGroup
                List<MenuGroup> _menuGroups = new ArrayList<MenuGroup>(Arrays.asList(_comp.value()));
                MenuGroup _menuGrop = targetClass.getAnnotation(MenuGroup.class);
                if (_menuGrop != null) {
                    _menuGroups.add(_menuGrop);
                }
                if (!_menuGroups.isEmpty()) {
                    List<MenuNodeGroup> _menuNodeGroups = new ArrayList<MenuNodeGroup>();
                    for (MenuGroup _item : _menuGroups) {
                        MenuNodeGroup _nodeGroup = new MenuNodeGroup();
                        _nodeGroup.setId(_item.id());
                        _nodeGroup.setIcon(__doTryLoadI18N(_comp, _item.icon()));
                        _nodeGroup.setName(__doTryLoadI18N(_comp, _item.name()));
                        _nodeGroup.setOrder(_item.order().value());
                        _nodeGroup.setDisplay(_item.displayType().equals(ISecurity.DisplayType.SHOW));
                        _nodeGroup.setPermission(_item.permission());
                        //
                        MenuNode[] _menuItemsArr = __doParseMenu(_comp, Arrays.asList(_item.value()));
                        Arrays.sort(_menuItemsArr, new Comparator<MenuNode>() {
                            public int compare(MenuNode o1, MenuNode o2) {
                                return o2.getOrder() - o1.getOrder();
                            }
                        });
                        _nodeGroup.menuNodes = _menuItemsArr;
                        //
                        _menuNodeGroups.add(_nodeGroup);
                    }
                    MenuNodeGroup[] _menuNodeGroupsArr = _menuNodeGroups.toArray(new MenuNodeGroup[_menuNodeGroups.size()]);
                    Arrays.sort(_menuNodeGroupsArr, new Comparator<MenuNodeGroup>() {
                        public int compare(MenuNodeGroup o1, MenuNodeGroup o2) {
                            return o2.getOrder() - o1.getOrder();
                        }
                    });
                    _meta.menuGroups = _menuNodeGroupsArr;
                }
                // @Menu
                List<Menu> _menus = new ArrayList<Menu>(Arrays.asList(_comp.menus()));
                Menu _menu = targetClass.getAnnotation(Menu.class);
                if (_menu != null) {
                    _menus.add(_menu);
                }
                if (!_menus.isEmpty()) {
                    MenuNode[] _menuItemsArr = __doParseMenu(_comp, _menus);
                    Arrays.sort(_menuItemsArr, new Comparator<MenuNode>() {
                        public int compare(MenuNode o1, MenuNode o2) {
                            return o2.getOrder() - o1.getOrder();
                        }
                    });
                    _meta.menus = _menuItemsArr;
                }
                //
                __COMPONENTS_CACHES.put(_id, _meta);
            }
            return _meta;
        }
        return null;
    }

    private static void __doParseMenuGroup() throws Exception {
    }

    private static MenuNode[] __doParseMenu(Components comp, List<Menu> _menus) throws Exception {
        List<MenuNode> _menuItems = new ArrayList<MenuNode>();
        for (Menu _m : _menus) {
            MenuNode _mItem = new MenuNode();
            _mItem.setId(_m.id());
            _mItem.setIcon(__doTryLoadI18N(comp, _m.icon()));
            _mItem.setName(__doTryLoadI18N(comp, _m.name()));
            _mItem.setOrder(_m.order().value());
            _mItem.setDisplay(_m.displayType().equals(ISecurity.DisplayType.SHOW));
            _mItem.setPermission(_m.permission());
            // @SubMenu
            List<SubMenu> _subMenus = new ArrayList<SubMenu>(Arrays.asList(_m.value()));
            List<MenuItem> _subMenuItems = new ArrayList<MenuItem>();
            for (SubMenu _subMenu : _subMenus) {
                MenuItem _subItem = new MenuItem();
                _subItem.setId(_subMenu.id());
                _subItem.setIcon(__doTryLoadI18N(comp, _subMenu.icon()));
                _subItem.setName(__doTryLoadI18N(comp, _subMenu.name()));
                _subItem.setOrder(_subMenu.order().value());
                _subItem.setDisplay(_subMenu.displayType().equals(ISecurity.DisplayType.SHOW));
                _subItem.setPermission(_subMenu.permission());
                //
                _subMenuItems.add(_subItem);
            }
            MenuItem[] _subMenuArr = _subMenuItems.toArray(new MenuItem[_subMenuItems.size()]);
            Arrays.sort(_subMenuArr, new Comparator<MenuItem>() {
                public int compare(MenuItem o1, MenuItem o2) {
                    return o2.getOrder() - o1.getOrder();
                }
            });
            _mItem.menuItems = _subMenuArr;
            //
            _menuItems.add(_mItem);
        }
        return _menuItems.toArray(new MenuNode[_menuItems.size()]);
    }

    public static Set<String> getComponentsNames() {
        return Collections.unmodifiableSet(__COMPONENTS_CACHES.keySet());
    }

    public static ComponentsMeta getComponents(String name) {
        return __COMPONENTS_CACHES.get(name);
    }

    public static Collection<ComponentsMeta> getComponents() {
        return Collections.unmodifiableCollection(__COMPONENTS_CACHES.values());
    }

    private ComponentsMeta() {
    }

    public MenuNodeGroup[] getMenuGroups() {
        return menuGroups;
    }

    public MenuNode[] getMenus() {
        return menus;
    }

    //
    // ----------
    //

    /**
     * 菜单组
     */
    public static class MenuNodeGroup extends AbstractComponents {

        private MenuNode[] menuNodes;

        MenuNodeGroup() {
        }

        public MenuNode[] getMenuNodes() {
            return menuNodes;
        }
    }

    /**
     * 菜单节点
     */
    public static class MenuNode extends AbstractComponents {

        private MenuItem[] menuItems;

        MenuNode() {
        }

        public MenuItem[] getMenuItems() {
            return menuItems;
        }
    }

    /**
     * 菜单项
     */
    public static class MenuItem extends AbstractComponents {
        MenuItem() {
        }
    }
}
