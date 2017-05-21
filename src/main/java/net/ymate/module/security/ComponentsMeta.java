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

    private MenuNode[] menuGroups;

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
                _meta.setName(_comp.name());
                _meta.setIcon(_comp.icon());
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
                    List<MenuNode> _menuNodes = new ArrayList<MenuNode>();
                    for (MenuGroup _item : _menuGroups) {
                        MenuNode _node = new MenuNode();
                        _node.setId(_item.id());
                        _node.setIcon(_item.icon());
                        _node.setName(_item.name());
                        _node.setOrder(_item.order().value());
                        _node.setDisplay(_item.displayType().equals(ISecurity.DisplayType.SHOW));
                        _node.setPermission(_item.permission());
                        //
                        List<MenuItem> _menuItems = new ArrayList<MenuItem>();
                        for (Menu _menu : _item.value()) {
                            MenuItem _menuItem = new MenuItem();
                            _menuItem.setId(_menu.id());
                            _menuItem.setIcon(_menu.icon());
                            _menuItem.setName(_menu.name());
                            _menuItem.setOrder(_menu.order().value());
                            _menuItem.setDisplay(_menu.displayType().equals(ISecurity.DisplayType.SHOW));
                            _menuItem.setPermission(_menu.permission());
                            //
                            _menuItems.add(_menuItem);
                        }
                        Collections.sort(_menuItems, new Comparator<MenuItem>() {
                            public int compare(MenuItem o1, MenuItem o2) {
                                return o1.getOrder() - o2.getOrder();
                            }
                        });
                        _node.menuItems = _menuItems.toArray(new MenuItem[_menuItems.size()]);
                        //
                        _menuNodes.add(_node);
                    }
                    Collections.sort(_menuNodes, new Comparator<MenuNode>() {
                        public int compare(MenuNode o1, MenuNode o2) {
                            return o1.getOrder() - o2.getOrder();
                        }
                    });
                    _meta.menuGroups = _menuNodes.toArray(new MenuNode[_menuNodes.size()]);
                }
                // @Menu
                List<Menu> _menus = new ArrayList<Menu>(Arrays.asList(_comp.menus()));
                Menu _menu = targetClass.getAnnotation(Menu.class);
                if (_menu != null) {
                    _menus.add(_menu);
                }
                if (!_menus.isEmpty()) {
                    List<MenuNode> _menuItems = new ArrayList<MenuNode>();
                    for (Menu _m : _menus) {
                        MenuNode _mItem = new MenuNode();
                        _mItem.setId(_m.id());
                        _mItem.setIcon(_m.icon());
                        _mItem.setName(_m.name());
                        _mItem.setOrder(_m.order().value());
                        _mItem.setDisplay(_m.displayType().equals(ISecurity.DisplayType.SHOW));
                        _mItem.setPermission(_m.permission());
                        //
                        _menuItems.add(_mItem);
                    }
                    Collections.sort(_menuItems, new Comparator<MenuNode>() {
                        public int compare(MenuNode o1, MenuNode o2) {
                            return o1.getOrder() - o2.getOrder();
                        }
                    });
                    _meta.menus = _menuItems.toArray(new MenuNode[_menuItems.size()]);
                }
                //
                __COMPONENTS_CACHES.put(_id, _meta);
            }
            return _meta;
        }
        return null;
    }

    public static Set<String> getComponentsNames() {
        return Collections.unmodifiableSet(__COMPONENTS_CACHES.keySet());
    }

    public static ComponentsMeta getComponents(String name) {
        return __COMPONENTS_CACHES.get(name);
    }

    private ComponentsMeta() {
    }

    public MenuNode[] getMenuGroups() {
        return menuGroups;
    }

    public MenuNode[] getMenus() {
        return menus;
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
